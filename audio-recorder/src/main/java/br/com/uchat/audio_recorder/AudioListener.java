package br.com.uchat.audio_recorder;

/**
 * @author netodevel
 */
public interface AudioListener {

    void onStop(RecordingItem recordingItem);

    void onCancel();

    void onError(Exception e);
}
