import java.util.*;
import java.awt.*;

public class GravityGun extends Weapon {
	public GravityGun(String name, int damage)
	{
		super(name, damage);
	}
	public void Shoot(Point Position, Point Speed, int Faction, Time Life)
	{
		ArrayList AnimationList = new ArrayList();
		Animation a = new Animation();
		Shot sh = new Shot(new Point(Position),new Point(32,32),Util.m_imGravImage, 1, false, false);
		sh.m_iDamage = 100;
		sh.m_iDamage += m_iPowerLevel;
		if (Life != null)
		{
			sh.m_tLife = new Time(Life);
			sh.m_tLife.AddMilliseconds(m_iPowerLevel * 20);
		}
		AnimationList = new ArrayList();
			a = new Animation("IDLE",1,8,true,34);
		AnimationList.add(a);

		sh.SetAnimations(AnimationList);
		sh.PlayAnimation("IDLE");
		
		sh.m_pSpeed.x = Speed.x;
		sh.m_pSpeed.y = Speed.y;

		sh.m_iFaction = Faction;
		Util.m_alSprite.add(sh);
		
		m_tLastShotTime = new Time(Util.m_tTime);
		
		if (m_iPowerLevel2 > 0)
		{
			sh = new Shot(new Point(Position),new Point(32,32),Util.m_imMissileImage, 1, false, false);
			sh.m_iDamage += m_iPowerLevel;
			sh.m_tLife = null;
			AnimationList = new ArrayList();
				a = new Animation("IDLE",1,3,true,6);
			AnimationList.add(a);
	
			sh.SetAnimations(AnimationList);
			sh.PlayAnimation("IDLE");
			
			sh.m_pSpeed.x = -5;
			sh.m_pSpeed.y = 0;
	
			sh.m_iFaction = Faction;
			Util.m_alSprite.add(sh);
			
						sh = new Shot(new Point(Position),new Point(32,32),Util.m_imMissileImage, 1, false, false);
			sh.m_iDamage += m_iPowerLevel;
			sh.m_tLife = null;
			AnimationList = new ArrayList();
				a = new Animation("IDLE",1,3,true,6);
			AnimationList.add(a);
	
			sh.SetAnimations(AnimationList);
			sh.PlayAnimation("IDLE");
			
			sh.m_pSpeed.x = 5;
			sh.m_pSpeed.y = 0;
	
			sh.m_iFaction = Faction;
			Util.m_alSprite.add(sh);	
		}
	}
}
