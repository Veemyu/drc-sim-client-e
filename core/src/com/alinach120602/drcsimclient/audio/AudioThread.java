package com.alinach120602.drcsimclient.audio;

import com.alinach120602.drcsimclient.net.NetUtil;
import com.alinach120602.drcsimclient.util.logging.Logger;

import static com.alinach120602.drcsimclient.Client.sockets;

/**
 * Created by rolando on 2/12/17.
 */
public class AudioThread extends Thread {
    private final AudioUtil audioUtil;
    private final NetUtil netUtil;
    private boolean running = true;

    public AudioThread() {
        this.audioUtil = new AudioUtil();
        this.setName("Network Thread: Audio");
        netUtil = new NetUtil();
    }

    @Override
    public void run() {
        while (running) {
            try {
                byte[] packet = netUtil.recv(sockets.socketAud);
                audioUtil.addData(packet);
            } catch (NetUtil.ReadTimeoutException ignore) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Logger.exception(e);
                }
            } catch (NetUtil.DisconnectedException e) {
                Logger.exception(e);
                Logger.info("Audio disconnected attempting to reconnect");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    Logger.exception(e);
                }
                netUtil.resetTimeout();
                sockets.reconnectAudio();
            }
        }
    }

    public void dispose() {
        running = false;
        audioUtil.dispose();
    }
}
