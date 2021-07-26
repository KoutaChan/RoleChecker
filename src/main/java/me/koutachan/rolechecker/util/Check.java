package me.koutachan.rolechecker.util;

import me.koutachan.rolechecker.RoleChecker;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
/*/
 * さすがに汚い？(再コード予定)
 */

public class Check {
    public boolean Checker(String uuid,String discordID){
        String[] result;
        if(discordID != null){
            result = new SQLUtil().request(uuid, discordID);
        }else {
            result = new SQLUtil().request(uuid, null);
        }
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
}
