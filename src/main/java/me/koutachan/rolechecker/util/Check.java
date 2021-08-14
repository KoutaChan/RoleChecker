package me.koutachan.rolechecker.util;

import me.koutachan.rolechecker.RoleChecker;
import net.dv8tion.jda.api.entities.Guild;
/*/
 * さすがに汚い？(再コード予定)
 */

public class Check {

    public static String allowedChannel;

    public boolean Checker(String uuid,String discordID){
        String[] result = new SQLUtil().request(uuid, discordID);
        try {
            if (result != null) {
                if (RoleChecker.plugin.getConfig().getString("serverid") != null) {
                    if(RoleChecker.jda.getGuildById(RoleChecker.plugin.getConfig().getString("serverid")).retrieveMemberById(result[1]).complete().getRoles().stream().anyMatch(i -> RoleChecker.list.contains(i.getId())))return true;
                } else {
                    for (Guild guild : RoleChecker.jda.getGuilds()) if(guild.retrieveMemberById(result[1]).complete().getRoles().stream().anyMatch(i -> RoleChecker.list.contains(i.getId())))return true;
                }
            }
        }catch (Exception ignored){
        }
    return false;
    }

    public boolean AllowedChecker(String channleId){
        if(allowedChannel == null){
            return true;
        }else{
            return allowedChannel.equals(channleId);
        }
    }
}
