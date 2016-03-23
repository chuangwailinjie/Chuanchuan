package space.peihao.chuanchuan.sound;

import java.io.IOException;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import space.peihao.chuanchuan.MainActivity;
import space.peihao.chuanchuan.R;

public class GameSoundPool {
	private MainActivity mainActivity;
	private SoundPool soundPool;
	private HashMap<Integer,Integer> map;
	@SuppressLint("UseSparseArrays")
	public GameSoundPool(MainActivity mainActivity){
		this.mainActivity = mainActivity;
		map = new HashMap<Integer,Integer>();
		soundPool = new SoundPool(2,AudioManager.STREAM_MUSIC,0);
	}
	public void initGameSound(){
		try {
			AssetManager am = mainActivity.getAssets();
			map.put(3, soundPool.load(am.openFd("sound_clear.mp3"), 1));
			map.put(4, soundPool.load(am.openFd("sound_game_over.mp3"), 1));
			map.put(5, soundPool.load(am.openFd("sound_level_change.mp3"), 1));
			map.put(6, soundPool.load(am.openFd("sound_match.mp3"), 1));
			map.put(7, soundPool.load(am.openFd("sound_pop.mp3"), 1));
			map.put(8, soundPool.load(am.openFd("sound_reset_food.mp3"), 1));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void playSound(int sound,int loop){
		AudioManager am = (AudioManager)mainActivity.getSystemService(Context.AUDIO_SERVICE);
		float stramVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		float stramMaxVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		float volume = stramVolumeCurrent/stramMaxVolumeCurrent;
		soundPool.play(map.get(sound), volume, volume, 1, loop, 1.0f);	
	}
}
