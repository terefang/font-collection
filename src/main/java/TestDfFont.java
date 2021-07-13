import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.ScreenUtils;
import lombok.SneakyThrows;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.lwjgl.opengl.DisplayMode;
import terefang.fonts.GdfBitmapFont;



public class TestDfFont implements ApplicationListener
{
    private LwjglApplication application;
    private OrthographicCamera hudCam;
    private SpriteBatch batch;
    private BitmapFont font;
    private BitmapFont font2;
    private DistanceFieldFont font3;
    private ShaderProgram dfshader;
    private BitmapFont font4;

    @SneakyThrows
    @Override
    public void create()
    {
        {

            this.font = //GdfBitmapFont.create8x16();
                    GdfBitmapFont.create(Gdx.files.absolute("/u/fredo/IdeaProjects/font-collection/src/main/resources/Terefang/gdf/cfnt/C_8x16_LE.gdf.gz"));
                    //GdfBitmapFont.create(Gdx.files.absolute("/u/fredo/IdeaProjects/font-collection/src/main/resources/Terefang/gdf/display.gdf"));
            this.font.setColor(1, 1, 1, 1);

            //this.font2 = new BitmapFont(Gdx.files.absolute("/u/fredo/IdeaProjects/font-collection/src/main/resources/Terefang/acbmf/cp8x8.fnt"));
            this.font2 = new BitmapFont(Gdx.files.absolute("/u/fredo/IdeaProjects/font-collection/src/main/resources/GoogleFonts/Lato/lato-lat-cyr-gr-16.fnt"));

            // /u/fredo/IdeaProjects/libgdx/tests/gdx-tests-android/assets/data/verdana39distancefield.fnt
            Texture texture = new Texture(Gdx.files.internal("/u/fredo/IdeaProjects/font-collection/lato-16-df.0.png"), true); // true enables mipmaps
            //Texture texture = new Texture(Gdx.files.internal("/u/fredo/IdeaProjects/libgdx/tests/gdx-tests-android/assets/data/verdana39distancefield.png"), true); // true enables mipmaps
            texture.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.Linear); // linear filtering in nearest mipmap image
            this.font3 = new DistanceFieldFont(Gdx.files.absolute("/u/fredo/IdeaProjects/font-collection/lato-16.fnt"), new TextureRegion(texture), false);
            // this.font3 = new DistanceFieldFont(Gdx.files.absolute("/u/fredo/IdeaProjects/libgdx/tests/gdx-tests-android/assets/data/verdana39distancefield.fnt"), new TextureRegion(texture), false);
            this.font3.setDistanceFieldSmoothing(1.5f);
            //this.font3.setUseIntegerPositions(true);
            this.font3.setColor(Color.WHITE);
            this.font3.setOwnsTexture(true);

            this.font4 = GdfBitmapFont.createFromRaw(32, 8,12, Gdx.files.absolute("/u/fredo/IdeaProjects/font-collection/poolrad.fon"));
            //this.font4.getData().setScale(3f);
            this.dfshader = DistanceFieldFont.createDistanceFieldShader();
            if (!this.dfshader.isCompiled()) {
                Gdx.app.error("fontShader", "compilation failed:\n" + this.dfshader.getLog());
            }
            this.hudCam = new OrthographicCamera();
            this.batch = new SpriteBatch();


        }
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
            this.font.draw(this.batch, "-- "+System.currentTimeMillis(), 0, this.font.getData().lineHeight);
            /*
            this.font.draw(this.batch, "--", 0, Gdx.graphics.getHeight()-128);
            this.font2.draw(this.batch, "-- "+System.currentTimeMillis(), 400, this.font2.getData().lineHeight);
            this.batch.setShader(this.dfshader);
            this.font3.getData().setScale(16f/32f);
            this.font3.draw(this.batch, "-- "+System.currentTimeMillis(), 0, Gdx.graphics.getHeight());
            this.font3.getData().setScale(128f/32f);
            this.font3.draw(this.batch, "-- "+System.currentTimeMillis(), 0, Gdx.graphics.getHeight());
            this.batch.setShader(null);
            this.font4.draw(this.batch, "-- "+System.currentTimeMillis(), 400, Gdx.graphics.getHeight());

            */
            int _l=0;
            int _h=0;
            for(int[] _range : MakeFont._chars)
            {
                for(int _i=_range[0]; _i<=_range[1]; _i++) {
                    if (_h % 80 == 0) {
                        _l++;
                    }

                    this.font4.draw(this.batch, Character.toString((char) _i), (_h%80) * 10, 600 - (_l * 12));

                    _h++;
                }
            }
            this.batch.end();
        }
    }

    @Override
    public void resize(int width, int height)
    {
        /*
        this.hudCam.setToOrtho(false, width, height);
        this.hudCam.update();
        this.batch.setProjectionMatrix(this.hudCam.combined);
        */
        OrthographicCamera guiCam = new OrthographicCamera();
        guiCam.setToOrtho(false, width, height);
        guiCam.update();
        this.batch.setProjectionMatrix(guiCam.combined);
        System.err.println("resized");
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    public static void main_(String[] args) throws Exception
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.useGL30 = false;
        config.vSyncEnabled = true;
        config.width = 800;
        config.height = 600;
        config.title = "Terefang LibGDX Contrib 3d Example";
        config.resizable = true;
        config.fullscreen = false;
        TestDfFont t = new TestDfFont();
        t.application = new LwjglApplication(t, config);

    }
    public static void main(String[] args) throws Exception
    {
        for(Graphics.Monitor _monitor : Lwjgl3ApplicationConfiguration.getMonitors())
        {
            System.err.println(_monitor.name);
            for(Graphics.DisplayMode _dm : Lwjgl3ApplicationConfiguration.getDisplayModes(_monitor))
            {
                System.err.println(String.format("%d x %d @ %d / %d", _dm.width, _dm.height, _dm.bitsPerPixel, _dm.refreshRate));
            }
        }
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.useOpenGL3(true, 4, 6);
        //config.setvSyncEnabled = true;
        config.setWindowedMode(800, 600);
        config.setTitle("Terefang LibGDX Contrib 3d Example");
        config.setResizable(true);
        //config.setFullscreenMode();
        TestDfFont t = new TestDfFont();
        Lwjgl3Application application = new Lwjgl3Application(t, config);

    }
}
