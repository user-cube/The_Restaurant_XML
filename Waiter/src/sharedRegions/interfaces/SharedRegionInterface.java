package sharedRegions.interfaces;

import commonStructures.Message;
import commonStructures.MessageException;

public interface SharedRegionInterface {

	Message processAndReply(Message inMessage) throws MessageException;
}
