import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import java.io.File;

public class Sound {

    private String filename;
    private Clip clip;
    private static FloatControl gainControl;
    private static Settings settings;

    public Sound(String filename){
        this.filename = filename;

        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play(boolean loop) {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResource(filename)));
            clip.start();
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(5);
            if (loop){clip.loop(Clip.LOOP_CONTINUOUSLY);}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateVolume(){
        try {
            gainControl.setValue(gainControl.getMinimum() + (gainControl.getMaximum() - gainControl.getMinimum() * settings.getBgvolume()));
        } catch (Exception e) {
            System.out.println("Sound Error");
        }
    }
}