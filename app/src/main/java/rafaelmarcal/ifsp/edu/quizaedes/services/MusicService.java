package rafaelmarcal.ifsp.edu.quizaedes.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import rafaelmarcal.ifsp.edu.quizaedes.R;

public class MusicService extends Service {
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        // Inicialize o MediaPlayer com a música desejada
        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.setLooping(true); // Loop infinito
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start(); // Inicie a música
        }
        return START_STICKY; // Serviço será reiniciado se for encerrado pelo sistema
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // Não é necessário, já que é um serviço em segundo plano
    }
}
