package info.adamjsmith.squarebomber.appwarp;

import info.adamjsmith.squarebomber.gpgs.ActionResolver;

import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;

public class WarpController {
	
	public WarpClient warpClient;
	
	public boolean isConnected;
	
	public WarpController() {
		initAppWarp();
		warpClient.addConnectionRequestListener(new ConnectionListener(this));
		warpClient.addChatRequestListener(new ChatListener(this));  
	    warpClient.addZoneRequestListener(new ZoneListener(this));  
	    warpClient.addRoomRequestListener(new RoomListener(this));  
	    warpClient.addNotificationListener(new NotificationListener(this));  
	}
	
	private void initAppWarp() {
		try {
			WarpClient.initialize(ActionResolver.apiKey, ActionResolver.secretKey);
			warpClient = WarpClient.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onConnectDone(boolean status) {
		if(status) {
			warpClient.initUDP();
			warpClient.joinRoomInRange(1, 1, false);
		} else {
			isConnected = false;
			handleError();
		}
	}
	
	public void onJoinRoomDone(RoomEvent event){  
	    if(event.getResult()==WarpResponseResultCode.SUCCESS){// success case  
	        this.roomId = event.getData().getId();  
	        warpClient.subscribeRoom(roomId);  
	    }else if(event.getResult()==WarpResponseResultCode.RESOURCE_NOT_FOUND){// no such room found  
	        HashMap<string, object=""> data = new HashMap<string, object="">();  
	        data.put("result", "");  
	        warpClient.createRoom("superjumper", "shephertz", 2, data);  
	    }else{  
	        warpClient.disconnect();  
	        handleError();  
	    }  
	}
}
