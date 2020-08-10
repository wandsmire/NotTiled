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
	public void setName(String transl, String name)
	{

		if (transl==""){
			this.name = name;
		}else
		{
			this.name = transl;
		}
	}

	public String getName()
	{
		return name;
	}
	
	public String getCurrent()
	{
		return steps.get(position).text;
	}
	public String getCurrentCue()
	{
		return steps.get(position).trigger;
	}
	public String getPrevious()
	{
		return steps.get(position-1).text;
	}

	public void addStep2(String text, String trigger, String criteria)
	{
		step a = new step();
		a.text=text;
		a.trigger=trigger;
		a.criteria=criteria;
		steps.add(a);
	}

	public void addStep(String transl, String trigger, String text)
	{
		step a = new step();

		if (transl==""){
			a.text=text;
		}else
		{
			a.text=transl;
		}
		a.trigger=trigger;
		steps.add(a);
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

	public boolean checkcue(String cue, String criteria)
	{
		if (steps.get(position).getTrigger().equalsIgnoreCase(cue) && steps.get(position).getCriteria().equalsIgnoreCase(cue))
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

		public String getCriteria() {
			return criteria;
		}

		public void setCriteria(String criteria) {
			this.criteria = criteria;
		}

		String criteria;

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
