package info.adamjsmith.squarebomber.gpgs;

public interface ActionResolver {
	public boolean getSignedInGPGS();
	public void loginGPGS();
	public void submitScoreGPGS(int score);
	public void unlockAchievementGPGS(String achievementId);
	public void getLeaderboardGPGS();
	public void getAchievementsGPGS();
	public void startQuickGame();
	public void joinQuickGame();
	public void showAds(boolean show);
}