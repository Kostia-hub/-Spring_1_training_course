package xmlConfig;

public class BlackWhiteCameraRoll implements CameraRoll {
    @Override
    public void processing() {
        System.out.println("save a black-white photo");
    }
}