package com.spacecombat;

public class FixedJoint extends Component {
	private GameObject attachedTo;
	private Vector2 lastPosition;
	
	public FixedJoint (GameObject thisIsAttachedTo)
	{
		attachedTo = thisIsAttachedTo;
		lastPosition = new Vector2(attachedTo.transform.position.x,attachedTo.transform.position.y);
	}	
	
	public void update()
	{
		if (attachedTo == null)
			return;
		
		//System.out.println(gameObject.getName() + " " + gameObject.transform.position);

		if (!attachedTo.transform.position.equals(lastPosition))
		{
			float xDiff = lastPosition.x - attachedTo.transform.position.x;
			float yDiff = lastPosition.y - attachedTo.transform.position.y;
			
			gameObject.transform.position.x -= xDiff;
			gameObject.transform.position.y -= yDiff;
			
			lastPosition.x = attachedTo.transform.position.x;
			lastPosition.y = attachedTo.transform.position.y;
		}	
	}
}
