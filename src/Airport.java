public class Airport {
    public static volatile Runway RUNWAY = new Runway();
    public static void main(String[] args) {
        Plane plane1 = new Plane("plane #1");
        Plane plane2 = new Plane("plane #2");
        Plane plane3 = new Plane("plane #3");
    }
    public static class Plane extends Thread{
        public Plane(String name) {
            super(name);
            start();
        }

        @Override
        public void run() {
            boolean isAlreadyTakeOff = false;
            while (!isAlreadyTakeOff){
                if (RUNWAY.getTakingOffPlane()==null){
                    RUNWAY.setTakingOffPlane(this);
                    System.out.println(getName()+ "takeoff");
                    takingOff();
                    System.out.println(getName()+ " already in the sky");
                    isAlreadyTakeOff = true;
                    RUNWAY.setTakingOffPlane(null);
                }else if (!this.equals(RUNWAY.getTakingOffPlane())){
                    System.out.println(getName()+ " waiting");
                    waiting();
                }
            }
        }
    }
    private static void waiting(){
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void takingOff(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
    }
    public static class Runway{
        private Thread t;
        public Thread getTakingOffPlane(){
            return t;
        }
        public void setTakingOffPlane(Thread t){
            synchronized (this){
                this.t=t;
            }
        }
    }
}
