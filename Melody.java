//Name: Ketan Vasudeva
//Course: ICS 4U1
//Purpose: Creates Melody object.

public class Melody {
	
	//Creates a note queue that will hold the song and its notes.
	public NoteQueue song;

	//Initializes the member variable.
	public Melody (NoteQueue n)
	{
		song = n;
	}
	//Returns total duration.
	public double getTotalDuration()
	{
		return song.time();
	}
	//Returns a String interpretation of the song.
	public String toString()
	{
		return song.makeString();
	}
	//Changes the tempo of the song (duration of each note) by a multiplier "tempo".
	public void changeTempo(double tempo)
	{
		song.tempoChange(tempo);
	}
	//Reverses the order of the notes of the notes.
	public void reverse()
	{
		song = song.reverseMel();
	}
	//Adds a song onto the end of another song.
	public void append(Melody other)
	{
		song.appendMel(other.song);
	}
	//Plays the loaded melody.
	public void play()
	{
		song.play();
	}
}
