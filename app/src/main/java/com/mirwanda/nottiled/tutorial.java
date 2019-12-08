package com.mirwanda.nottiled;
import java.util.*;

public class tutorial
{
	String name;
	int position;
	List<step> steps=new ArrayList<step>();

	public void setPosition(int position)
	{
		this.position = position;
	}

	public int getPosition()
	{
		return position;
	}

	public void setSteps(List<step> steps)
	{
		this.steps = steps;
	}

	public List<step> getSteps()
	{
		return steps;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
	
	public String getCurrent()
	{
		return steps.get(position).text;
	}
	
	public void addStep(String trigger, String text)
	{
		step a = new step();
		a.text=text;
		a.trigger=trigger;
		steps.add(a);
	}
	
	public void start()
	{
		position=0;
	}
	
	public String next()
	{
		position+=1;
		return steps.get(position).getText();
	}
	
	public void reset()
	{
		position=0;
	}
	
	public boolean checkcue(String cue)
	{
		if (steps.get(position).getTrigger().equalsIgnoreCase(cue))
		{
			return true;
		}
		return false;
	}
	
	public boolean isEnded()
	{
		if (steps.size()-1==position) return true;
		return false;
	}
	
	static class step
	{
		String text;
		String trigger;


		public void setText(String text)
		{
			this.text = text;
		}

		public String getText()
		{
			return text;
		}

		public void setTrigger(String trigger)
		{
			this.trigger = trigger;
		}

		public String getTrigger()
		{
			return trigger;
		}}
}
