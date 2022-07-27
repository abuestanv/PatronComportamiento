/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oscarblancarte.ipd.state.states;

import oscarblancarte.ipd.state.Server;

/**
 *
 * @author ABUESTAN
 */
public class ShutdownSecureState extends AbstractServerState {

    private Thread monitoringThread;

    public ShutdownSecureState(final Server server){ 
        server.getMessageProcess().start();
        monitoringThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000 * 5);
                        if (server.getMessageProcess().countMessage() == 0) {
                            server.setState(new StopServerState(server));
                            break;
                        }
                        System.out.println("Procesing data.....");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        monitoringThread.start();
    }
    
    
    @Override
    public void handleMessage(Server server, String message) {
        System.out.println("Cant´t process request, server in process of safe shutdown"); }


}
