package info.adamjsmith.squarebomber.appwarp;

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;

public class ConnectionListener implements ConnectionRequestListener {
	
	WarpController callBack;
	
	public ConnectionListener(WarpController callBack) {
		this.callBack = callBack;
	}
	
	@Override
	public void onConnectDone(ConnectEvent e) {
		if(e.getResult() == WarpResponseResultCode.SUCCESS) {
			callBack.onConnectDone(true);
		} else {
			callBack.onConnectDone(false);
		}
	}

	@Override
	public void onDisconnectDone(ConnectEvent arg0) {
		
	}

	@Override
	public void onInitUDPDone(byte result) {
		if(result == WarpResponseResultCode.SUCCESS) {
			callBack.isUDPEnabled = true;
		}
	}

}
