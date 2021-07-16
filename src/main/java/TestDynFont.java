import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.SneakyThrows;
import terefang.fonts.DynamicFreeTypeFonter;
import terefang.fonts.FreeTypeTestFontGenerator;


public class TestDynFont implements ApplicationListener
{
    private LwjglApplication application;
    private OrthographicCamera hudCam;
    private SpriteBatch batch;
    private BitmapFont[] font;
    Viewport viewport;
    private DynamicFreeTypeFonter fonter;

    @SneakyThrows
    @Override
    public void create()
    {
        this.fonter = new DynamicFreeTypeFonter(Gdx.files.local("VenturisSansADFEx-Bold.otf"));
        this.fonter.addCodeRanges(DynamicFreeTypeFonter.CODERANGE_LATIN);
        this.font = new BitmapFont[256];
        int _fs = 16;
        int _ws = 0;
        int _sf = 0;
        while(_ws<600)
        {
            this.font[_sf] = this.fonter.getFont(_fs, true);
            _sf++;
            _ws+=_fs;
            _fs= (_fs*125)/100;
        }

        this.hudCam = new OrthographicCamera();
        this.hudCam.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.viewport = new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), this.hudCam);
        this.viewport.setScreenPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        this.batch = new SpriteBatch();

    }

    @Override
    public void dispose() {
        this.batch.dispose();
        for(BitmapFont _f : this.font)
        {
            if(_f!=null) _f.dispose();
        }
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


            int _ws = 0;
            int _sf = 0;
            while(this.font[_sf] != null)
            {
                this.font[_sf].draw(this.batch, "1234567890 !§%&/()=? ƒ¢Ȼ$£¥ QWERTZUIOPASDFGHJKLYXCVBNMqwertzuiopasdfghjklyxcvbnm+-'~*", 10, 600 - _ws);

                _ws+=this.font[_sf].getLineHeight();
                _sf++;
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
        this.viewport.apply();
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
        TestDynFont t = new TestDynFont();
        Lwjgl3Application application = new Lwjgl3Application(t, config);

    }
}
