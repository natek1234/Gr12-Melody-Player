//Name: Ketan Vasudeva
//Course: ICS 4U1
//Purpose: Creates NoteQueue to hold note objects.
public class NoteQueue {
	//Creates two nodes, one for front and one for back of queue.
	Node front;
	Node back;

	//Initializes front and back nodes.
	public NoteQueue()
	{
		front = null;
		back = null;
	}
	//Adds item to the back of the queue.
	public void enqueue(Object item) 
	{

		if(front == null)
		{
			front = new Node(item);
			back = front;
		}
		else 
		{
			back.setNext(new Node(item));
			back = back.getNext();
		}

	}

	//Removes and returns item from the front of queue.
	public Object dequeue()
	{
		if(front == back)
		{
			Node n = front;
			front = null;
			back = null;
			return n.getItem();
		}
		if(front != null)
		{
			Node n = front;
			front = front.getNext();
			return n.getItem();
		}
		return null;
	}

	//Checks if queue is empty.
	public boolean isEmpty() {
		if(front == null)
		{
			return true;
		}
		return false;
	}

	//Returns time of queue.
	public double time() {
		//Starts count at 0.
		double count = 0;
		Node n = front;
		if(front == null)
		{
			return 0;
		}
		else
		{
			//Loops through queue.
			while(!n.equals(back))
			{
				//Runs if note's repeat value is true.
				if(((Note) n.getItem()).isRepeat())
				{
					//Creates new queue to hold repeated section.
					NoteQueue m = new NoteQueue();
					//Loads the first note into queue.
					m.enqueue(n.getItem());
					//Adds note duration onto count.
					count = count + ((Note) n.getItem()).getDuration();
					//Moves onto next node.
					n = n.getNext();
					//Loops until finds next repeat value as true (end of repeated section)
					while (!((Note) n.getItem()).isRepeat())
					{
						//Loads all repeated notes into new queue and adds duration values to count.
						m.enqueue(n.getItem());
						count = count + ((Note) n.getItem()).getDuration();
						n = n.getNext();
					}
					//Loads last note of repeat into the queue.
					m.enqueue(n.getItem());
					count = count + ((Note) n.getItem()).getDuration();
					//If the last note of the repeat is not the end of the song, n moves onto the next node.
					if(n.getNext() != null)
					{
						n = n.getNext();
					}
					//The duration of the repeated section is added onto count.
					count = count + m.timeRepeat();
				}
				//For all notes which are not repeated, duration values are simply added onto count.
				else
				{
					count = count +  ((Note) n.getItem()).getDuration();
					n = n.getNext();
				}
			}
			//Adds duration of last note if the back is not the end of a repeated section.
			if(!((Note) back.getItem()).isRepeat())
			{
				count = count +  ((Note) n.getItem()).getDuration();
			}
			//Returns the count.
			return count;
		}
	}
	//This method is called to find the duration of repeated sections, only runs through once.
	public double timeRepeat()
	{
		Node n = front;
		//Starts count at 0.
		double count = 0;
		if(front == null)
		{
			return 0;
		}
		else
		{
			//Loops through queue of repeated notes and adds durations to count.
			while(!n.equals(back))
			{
				count = count +  ((Note) n.getItem()).getDuration();
				n = n.getNext();
			}
			//Adds duration of last note to count.
			count = count +  ((Note) n.getItem()).getDuration();
			return count;
		}
	}

	//Returns size of queue.
	public int size() {
		int count = 1;
		Node n = front;
		if(front == null)
		{
			return 0;
		}
		else
		{
			while(!n.equals(back))
			{
				count++;
				n = n.getNext();
			}
			return count;
		}
	}
	
	//Returns String of queue.
	public String makeString() {
		Node n = front;
		//Creates blank string.
		String text = "";
		if(front == null)
		{
			return "";
		}
		else
		{
			//Loops through queue, added String interpretation of each note onto the String text.
			while(!n.equals(back))
			{
				text = text + (((Note) n.getItem()).toString()) + "\n";
				n = n.getNext();
			}
			//Adds on last note in song.
			text = text + (((Note) n.getItem()).toString());
			return text;
		}
	}

	public void tempoChange(double tempo) {
		Node n = front;
		if(front == null)
		{
			return;
		}
		else
		{
			//Loops through queue, changing durations by a multiplier.
			while(!n.equals(back))
			{
				((Note) n.getItem()).setDuration(((Note) n.getItem()).getDuration() * tempo);
				n = n.getNext();
			}
			//Multiples duration of last note.
			((Note) n.getItem()).setDuration(((Note) n.getItem()).getDuration() * tempo);
		}
	}
	
	//PLays the song.
	public void play()
	{
		Node n = front;
		if(front == null)
		{
			return;
		}
		else
		{
			//Loops through queue.
			while(!n.equals(back))
			{
				//Runs if the note is start of repeated section
				if(((Note) n.getItem()).isRepeat())
				{
					//Creates new queue to store repeated section.
					NoteQueue m = new NoteQueue();
					//Loads note onto repeat queue.
					m.enqueue(n.getItem());
					//Plays note.
					((Note) n.getItem()).play();
					//Sets n to next.
					n = n.getNext();
					//Loops through till finds end of repeated section, loading notes into repeat queue and playing them.
					while (!((Note) n.getItem()).isRepeat())
					{
						m.enqueue(n.getItem());
						((Note) n.getItem()).play();
						n = n.getNext();
					}
					//Loads and plays last note.
					m.enqueue(n.getItem());
					((Note) n.getItem()).play();
					//If n is not the back, n is changed to next.
					if(n.getNext() != null)
					{
						n = n.getNext();
					}
					//Plays the queue of the repeated section.
					m.playRepeat();
				}
				//Otherwise just plays note and moves on.
				else
				{
					((Note) n.getItem()).play();
					n = n.getNext();
				}
			}
			//If the back is not part of repeated section, plays back note.
			if(!((Note) back.getItem()).isRepeat())
			{
				((Note) n.getItem()).play();
			}
		}
	}

	//Plays repeated sections.
	public void playRepeat()
	{
		Node n = front;
		if(front == null)
		{
			return;
		}
		else
		{
			//Loops through queue, playing every note.
			while(!n.equals(back))
			{

				((Note) n.getItem()).play();
				n = n.getNext();
			}
			((Note) n.getItem()).play();
		}
	}

	//Adds one melody onto the end of another.
	public void appendMel(NoteQueue other)
	{
		Node n = other.front;
		if(front == null)
		{
			return;
		}
		else
		{
			//Loops through, adding notes of new queue onto the back of the current NoteQueue.
			while(!n.equals(other.back))
			{
				back.setNext(n);
				back = back.getNext();
				n = n.getNext();
			}
			back.setNext(n);
			back = back.getNext();
		}
	}

	//Reverses the order of the notes of a song.
	public NoteQueue reverseMel()
	{
		Node n = front;
		//Creates a temporary stack.
		NoteStack m = new NoteStack();
		if(front == null)
		{
			return null;
		}
		else
		{
			//Loops through, adding notes into stack.
			while(!n.equals(back))
			{
				m.push(n.getItem());
				n = n.getNext();
			}
			m.push(n.getItem());
		}
		//Creates new NoteQueue to reverse order into.
		NoteQueue x = new NoteQueue();
		n = m.top;

		//Loops through stack, popping top value into new Queue (reverses order since Stack will return notes backwards)
		while(n != null)
		{
			x.enqueue(m.pop());
			n = m.top;
		}	
		//Returns new NoteQueue.
		return x;
	}

}
