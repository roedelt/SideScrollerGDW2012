package gdw.network.messageType;

import java.nio.ByteBuffer;
import java.util.LinkedList;

import gdw.network.NetMessageType;

public class DeadReckoningNetMessage extends NetMessageType
{
	public final int entityID;
	public final int sequenceID;
	public final float posX;
	public final float posY;
	public final float velocityX;
	public final float velocityY;
	public final float roundTipTime;

	public DeadReckoningNetMessage(int id, int seqID, float posX, float posY, float veloX, float veloY, float roundTip)
	{
		this.entityID = id;
		this.sequenceID = seqID;
		this.posX = posX;
		this.posY = posY;
		this.velocityX = veloX;
		this.velocityY = veloY;
		this.roundTipTime = roundTip;
	}
	
	private DeadReckoningNetMessage(ByteBuffer buf)
	{
		this.entityID = buf.getInt();
		this.sequenceID = buf.getInt();
		this.posX = buf.getFloat();
		this.posY = buf.getFloat();
		this.velocityX = buf.getFloat();
		this.velocityY = buf.getFloat();
		this.roundTipTime = buf.getFloat();
	}
	
	public static DeadReckoningNetMessage[] getFromByteBuffer(ByteBuffer buf)
	{
		int length = buf.get();
		DeadReckoningNetMessage [] arr = new DeadReckoningNetMessage[length];
		for(int i=0;i<length;++i)
		{
			arr[i] = new DeadReckoningNetMessage(buf);
		}
		return arr;
	}
	
	public static void fillInByteBuffer(LinkedList<DeadReckoningNetMessage> list,ByteBuffer buf , int maxAmount)
	{
		buf.put(NetMessageType.DeadReckoningMessageType);
		if(list.size() > maxAmount)
		{
			buf.put((byte)maxAmount);
		}else
		{
			buf.put((byte)list.size());
		}
		for(int i=0;i<maxAmount;++i)
		{
			DeadReckoningNetMessage msg = list.poll();
			if(msg == null)
				return;
			buf.putInt(msg.entityID);
			buf.putInt(msg.sequenceID);
			buf.putFloat(msg.posX);
			buf.putFloat(msg.posY);
			buf.putFloat(msg.velocityX);
			buf.putFloat(msg.velocityY);
			buf.putFloat(msg.roundTipTime);
		}
		
	}
}
