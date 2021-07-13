import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.DistanceFieldFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.SneakyThrows;
import terefang.fonts.FreeTypeTestFontGenerator;
import terefang.fonts.GdfBitmapFont;


public class TestBdfFont implements ApplicationListener
{
    private LwjglApplication application;
    private OrthographicCamera hudCam;
    private SpriteBatch batch;
    private BitmapFont font;
    Viewport viewport;

    @SneakyThrows
    @Override
    public void create()
    {

        FreeTypeTestFontGenerator.FreeTypeFontParameter _param = new FreeTypeTestFontGenerator.FreeTypeFontParameter();
        _param.size = 16;
        StringBuilder _sb = new StringBuilder();
        _sb.append((char)0);
        for(int[] _range : MakeFont._chars)
        {
            for(int _i=_range[0]; _i<=_range[1]; _i++)
                _sb.append((char)_i);
        }
        _param.characters=_sb.toString();
        this.font = //GdfBitmapFont.create8x16();
                //new FreeTypeFontGenerator(Gdx.files.local("src/main/resources/lucy/tewi-medium-11.pcf")).generateFont(_param);
                //GdfBitmapFont.create(Gdx.files.local("src/main/resources/Terefang/gdf/cfnt/C_8x16_LE.gdf.gz"));
                //GdfBitmapFont.create(Gdx.files.local("src/main/resources/Terefang/gdf/display.gdf"));
                //GdfBitmapFont.createFromRaw(0,8,16, Gdx.files.local("3dfx8x16.bin"));
                new FreeTypeTestFontGenerator(Gdx.files.local("src/main/resources/ProFont/ProFontWindows.ttf")).generateFont(_param);
        this.font.setColor(1, 1, 1, 1);
        this.font.setUseIntegerPositions(true);
        //this.font.getData().setScale(1.5f);

        this.hudCam = new OrthographicCamera();
        this.hudCam.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.viewport = new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), this.hudCam);
        this.viewport.setScreenPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        this.batch = new SpriteBatch();

    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.font.dispose();
    }

    @Override
    public void render()
    {
        {
            ScreenUtils.clear(0, 0, 0.5f, 0, true);
        }

        {
            this.batch.begin();
            //this.batch.setTransformMatrix(this.viewport.getCamera().view);

            int _l=0;
            int _h=0;
            for(int[] _range : MakeFont._chars)
            {
                for(int _i=_range[0]; _i<=_range[1]; _i++) {
                    if (_h % 80 == 0) {
                        _l++;
                    }

                    this.font.draw(this.batch, Character.toString((char) _i), (_h%80) * 10, 600 - (_l * 12));

                    _h++;
                }
            }
            this.batch.end();
        }
    }

    @Override
    public void resize(int width, int height)
    {
        //this.hudCam.setToOrtho(false, width, height);
        //this.hudCam.update();
        //this.batch.setProjectionMatrix(this.hudCam.combined);
        /*
        OrthographicCamera guiCam = new OrthographicCamera();
        guiCam.setToOrtho(false, width, height);
        guiCam.update();
        this.batch.setProjectionMatrix(guiCam.combined);
        */
        this.viewport.update(width, height);
        this.batch.setProjectionMatrix(this.viewport.getCamera().combined);
        System.err.println("resized");
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    public static void main(String[] args) throws Exception
    {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.useOpenGL3(true, 4, 6);
        //config.setvSyncEnabled = true;
        config.setWindowedMode(800, 600);
        config.setTitle("Terefang LibGDX Contrib 3d Example");
        config.setResizable(true);
        //config.setFullscreenMode();
        boolean found=false;
        for(Graphics.Monitor _monitor : Lwjgl3ApplicationConfiguration.getMonitors())
        {
            System.err.println(_monitor.name);
            for(Graphics.DisplayMode _dm : Lwjgl3ApplicationConfiguration.getDisplayModes(_monitor))
            {
                System.err.println(String.format("%d x %d @ %d / %d", _dm.width, _dm.height, _dm.bitsPerPixel, _dm.refreshRate));
                //if(_dm.height==600 && _dm.width==800)
                //{
                //    config.setFullscreenMode(_dm);
                //    found=true;
                //}
                if(found) break;
            }
            if(found) break;
        }
        TestBdfFont t = new TestBdfFont();
        Lwjgl3Application application = new Lwjgl3Application(t, config);

    }
}
