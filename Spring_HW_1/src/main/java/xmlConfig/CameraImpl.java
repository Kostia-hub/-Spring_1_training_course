package xmlConfig;

public class CameraImpl implements Camera{
    private CameraRoll cameraRoll;

    public CameraImpl() {
    }

    public CameraImpl(CameraRoll cameraRoll) {
        this.cameraRoll = cameraRoll;
    }

    public void doPhotograph(){
        System.out.println("Chick - chick");
        cameraRoll.processing();
    }

    public CameraRoll getCameraRoll() {
        return cameraRoll;
    }

    public void setCameraRoll(CameraRoll cameraRoll) {
        this.cameraRoll = cameraRoll;
    }
}