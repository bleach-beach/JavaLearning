package threadPattern.guardedSuspension.interrupt;



public class Main {
    public static void main(String[] args) {
        //�����߳�
        RequestQueue requestQueue = new RequestQueue();
        Thread alice = new ClientThread(requestQueue, "Alice", 314159L);
        Thread bobby = new ServerThread(requestQueue, "Bobby", 265358L);
        alice.start();
        bobby.start();

        //�ȴ�Լ10���
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }

        //����interrupt����
        System.out.println("***** calling interrupt *****");
        alice.interrupt();
        bobby.interrupt();
    }
}
