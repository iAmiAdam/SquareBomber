package info.adamjsmith.squarebomber.android;

import info.adamjsmith.squarebomber.SquareBomber;
import info.adamjsmith.squarebomber.objects.Player;
import info.adamjsmith.squarebomber.screens.MainMenuScreen;
import info.adamjsmith.squarebomber.screens.MultiplayerGame;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;

import com.badlogic.gdx.Gdx;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.example.games.basegameutils.GameHelper;

public class MultiplayerServices implements RoomUpdateListener, RealTimeMessageReceivedListener, RoomStatusUpdateListener {
	
	SquareBomber game;
	GameHelper gh;
	Activity ctx;
	String roomID = null;
	ArrayList<String> participants;
	String myID;
	Player[] players;
	
	public MultiplayerServices(GameHelper gh, Activity ctx, SquareBomber game) {
		this.gh = gh;
		this.ctx = ctx;
		this.game = game;
	}
	
	@Override
	public void onJoinedRoom(int statusCode, Room room) {
		
	}

	@Override
	public void onLeftRoom(int statusCode, String roomId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRoomConnected(int statusCode, Room room) {
		roomID = room.getRoomId();
		myID = room.getParticipantId(Games.Players.getCurrentPlayerId(gh.getApiClient()));
		participants = room.getParticipantIds();
		
		players = new Player[participants.size()];
		
		int i = 0;
		for (String s : participants) {
			players[i] = new Player(s);
			Gdx.app.log(String.valueOf(i), s);
			i++;
		}		
		
		Gdx.app.log("MyID", myID);
		
		//game.setScreen(new MultiplayerGame(game, players, myID));
	}

	@Override
	public void onRoomCreated(int statusCode, Room room) {
		Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(gh.getApiClient(), room, 2);
		ctx.startActivityForResult(i, 2);
	}

	@Override
	public void onRealTimeMessageReceived(RealTimeMessage message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectedToRoom(Room room) {
		
	}

	@Override
	public void onDisconnectedFromRoom(Room room) {
		game.setScreen(new MainMenuScreen(game));
	}

	@Override
	public void onP2PConnected(String participantId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onP2PDisconnected(String participantId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPeerDeclined(Room arg0, List<String> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPeerInvitedToRoom(Room arg0, List<String> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPeerJoined(Room arg0, List<String> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPeerLeft(Room arg0, List<String> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPeersConnected(Room arg0, List<String> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPeersDisconnected(Room arg0, List<String> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRoomAutoMatching(Room room) {

	}

	@Override
	public void onRoomConnecting(Room room) {

	}
	
	public void sendReliableMessage(Byte[] messageData, int roomId) {
		//Games.RealTimeMultiplayer.sendReliableMessage(gh.getApiClient(), null, messageData, roomId, 2);
	}

}
