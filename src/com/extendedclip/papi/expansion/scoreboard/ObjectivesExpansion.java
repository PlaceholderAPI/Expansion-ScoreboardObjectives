package com.extendedclip.papi.expansion.scoreboard;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class ObjectivesExpansion extends PlaceholderExpansion {
	
	public boolean canRegister() {
		return true;
	}
	
	public String getAuthor() {
		return "L3thalBunny";
	}
	
	public String getName() {
		return "ScoreboardObjectives";
	}
	
	public String getIdentifier() {
		return "objective";
	}
	
	public String getPlugin() {
		return null;
	}
	
	public String getVersion() {
		return "3.3.1";
	}
	
	public List<String> getPlaceholders() {
		List<String> list = new ArrayList<String>();
		
		list.add(plc("displayname_<obj-name>"));
		list.add(plc("score_{<obj-name>}"));
		list.add(plc("score_{<obj-name>}_{[otherEntry]}"));
		list.add(plc("scorep_{<obj-name>}"));
		list.add(plc("scorep_{<obj-name>}_{[otherPlayer]}"));
		
		return list;
	}
	
	private String plc(String str) {
		return "%" + getIdentifier() + "_" + str + "%";
	}
	
	public String onPlaceholderRequest(Player p, String identifier) {
		if (identifier.startsWith("score_")) {
			identifier = identifier.replace("score_", "");
			String[] fields = identifier.split("\\}_", 2);
			fields[0] = fields[0].replaceAll("\\{", "").replaceAll("\\}", "");
			String entry = "";
			if (fields.length > 1) {
				fields[1] = fields[1].replaceAll("\\{", "").replaceAll("\\}", "");
				entry = fields[1];
			} else if (p != null) {
				entry = p.getName();
			} else {
				return "PNF";
			}
			String objName = fields[0];
			Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
			Objective obj = board.getObjective(objName);
			Score score = obj.getScore(entry);
			int num = score.getScore();
			
			return num + "";
		}
		if (identifier.startsWith("scorep_")) {
			identifier = identifier.replace("scorep_", "");
			String[] fields = identifier.split("\\}_", 2);
			fields[0] = fields[0].replaceAll("\\{", "");
			String player = "";
			if (fields.length > 1) {
				fields[1] = fields[1].replaceAll("\\{", "").replaceAll("\\}", "");
				player = getOnlinePlayer(fields[1]);
				if (player == null) {
					player = getOfflinePlayer(fields[1]);
				}
				if (player == null) {
					return "PNF";
				}
			} else if (p != null) {
				player = p.getName();
			} else {
				return "PNF";
			}
			String objName = fields[0];
			Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
			Objective obj = board.getObjective(objName);
			Score score = obj.getScore(player);
			int num = score.getScore();
			
			return num + "";
		}
		if (identifier.startsWith("displayname_")) {
			String objName = identifier.replace("displayname_", "");
			Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
			try {
				Objective obj = board.getObjective(objName);
				return obj.getDisplayName();
			} catch (Exception e) {
				return "ObjDNE";
			}
		}
		return null;
	}
	
	private String getOnlinePlayer(String player) {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (p.getName().equalsIgnoreCase(player)) {
				return p.getName();
			}
		}
		return null;
	}
	
	private String getOfflinePlayer(String player) {
		OfflinePlayer[] arrayOfOfflinePlayer;
		int j = (arrayOfOfflinePlayer = Bukkit.getOfflinePlayers()).length;
		for (int i = 0; i < j; i++) {
			OfflinePlayer p = arrayOfOfflinePlayer[i];
			if (p.getName().equalsIgnoreCase(player)) {
				return p.getName();
			}
		}
		return null;
	}
}
