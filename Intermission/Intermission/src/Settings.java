public class Settings {

    boolean fullscreen = false;
    float scale = 1;
    int currentresolution = 0;

    private static float bgvolume = 1;
    private static float sfxvolume = 1;

    /*************************************************
    set methods
     ************************************************/

    public void lowerbgvolume() {
        if (bgvolume > 0) {
            bgvolume -= 0.1;
        }
    }

    public void increasebgvolume() {
        if (bgvolume < 1) {
            bgvolume += 0.1;
        }
    }

    public void lowersfxvolume() {
        if (sfxvolume > 0) {
            sfxvolume -= 0.1;
        }
    }

    public void increasesfxvolume() {
        if (sfxvolume < 100) {
            sfxvolume += 0.1;
        }
    }

    public void setFullscreen(boolean isFullscreen) {fullscreen = isFullscreen;}

    public void setCurrentresolution(int currentresolution) {this.currentresolution = currentresolution;}

    public void setScale(float scale) {this.scale = scale;}

    /*************************************************
     get methods
     ************************************************/

    public static float getBgvolume() { return bgvolume;}

    public static float getSfxvolume() { return sfxvolume;}

    public int getCurrentresolution() { return currentresolution;}

    public boolean isfullscreen() {return fullscreen;}
}
