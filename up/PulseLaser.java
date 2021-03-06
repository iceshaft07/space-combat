import java.util.*;
import java.awt.*;

public class PulseLaser extends Weapon{
	public PulseLaser(String name, int damage, int damage2, int owner)
	{
		super(name, damage, damage2, owner);
	}
	public void Shoot(Point Position, Point Speed, int Faction, Time Life)
	{
		int max = (int)(Util.m_rcScreen.height / 32)+1;
		Shot sh;
		ArrayList AnimationList = new ArrayList();
		Animation a = new Animation();
		for (int x = 0; x < max; x++)
		{
			AnimationList = new ArrayList();
			a = new Animation();
			sh = new Shot(new Point(Position.x,Position.y - (32 * x)),new Point(32,32),Util.m_imPulseImage, 1, m_iOwner, false, false);
			sh.AddImage(Util.m_imPulseImageNV);
			sh.m_iDamage = m_iBaseDamage+m_iPowerLevel;
			if (Life != null)
				sh.m_tLife = new Time(Life);
			int z = 5-(int)((m_iPowerLevel-1)/1.8);
			if (x <= 4)
			{
				z+=(4-x);
			}
			if (z > 5)
				z = 5;
			AnimationList = new ArrayList();
				a = new Animation("IDLE",z,5,false,6);
			AnimationList.add(a);
	
			sh.SetAnimations(AnimationList);
			sh.PlayAnimation("IDLE");
			
			sh.m_pSpeed.x = 0;
			sh.m_pSpeed.y = 0;
	
			sh.m_iFaction = Faction;
			sh.SetImage(Util.m_iVision);
			Util.m_alSprite.add(sh);
			
			m_tLastShotTime = new Time(Util.m_tTime);
		}
	}
	public void ShootMissile(Point Position, Point Speed, int Faction, Time Life)
	{
		Shot sh;
		ArrayList AnimationList = new ArrayList();
		Animation a = new Animation();
		if (m_iPowerLevel2 > 0)
		{
			sh = new Shot(new Point(Position),new Point(32,32),Util.m_imMissileImage, 1, m_iOwner, false, false);
			sh.AddImage(Util.m_imMissileImageNV);
			sh.m_iDamage = m_iMissileBaseDamage+m_iPowerLevel2;

			sh.m_tLife = null;

			AnimationList = new ArrayList();
				a = new Animation("IDLE",1,3,true,3);
			AnimationList.add(a);
	
			sh.SetAnimations(AnimationList);
			sh.PlayAnimation("IDLE");
			
			sh.m_pSpeed.x = -7;
			sh.m_pSpeed.y = -8;
	
			sh.m_iFaction = Faction;
			sh.SetImage(Util.m_iVision);
			Util.m_alSprite.add(sh);
			//----------------------------
			sh = new Shot(new Point(Position),new Point(32,32),Util.m_imMissileImage, 1, m_iOwner, false, false);
			sh.AddImage(Util.m_imMissileImageNV);
			sh.m_iDamage = m_iMissileBaseDamage+m_iPowerLevel2;

			sh.m_tLife = null;

			AnimationList = new ArrayList();
				a = new Animation("IDLE",1,3,true,3);
			AnimationList.add(a);
	
			sh.SetAnimations(AnimationList);
			sh.PlayAnimation("IDLE");
			
			sh.m_pSpeed.x = 7;
			sh.m_pSpeed.y = -8;
	
			sh.m_iFaction = Faction;
			sh.SetImage(Util.m_iVision);
			Util.m_alSprite.add(sh);	
		}
		m_tMissileLastShotTime = new Time(Util.m_tTime);
	}
}
