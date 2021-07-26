package me.koutachan.rolechecker.api.event;

/*
 ゴミイベントAPIのサンプル
 */

/*
 event listener:
 new EventListener().addListener(new testAPI());
 EventListener.observers.add(new testAPI());
 */
public class testAPI implements EventListener.Listener {

    @Override
    public void Event(EventListener.Event event) {
        switch (event.getReason()){
            case JOIN:
                event.setEmbedBuilder(event.getEmbedBuilder().addField("これは:","Joinコマンドでした",false));
                if(event.isSuccess())event.setEmbedBuilder(event.getEmbedBuilder().addField("成功しました！","おめでとう！！",false));
                break; //ちゃんと壊す
            case REMOVE:
                event.setEmbedBuilder(event.getEmbedBuilder().addField("これは:","Removeコマンドでした",false));
                break;
            case FORCEJOIN:
                event.setEmbedBuilder(event.getEmbedBuilder().addField("これは:","ForceJoinコマンドでした",false));
                break;
            case FORCEREMOVE:
                event.setEmbedBuilder(event.getEmbedBuilder().addField("これは:","ForceRemoveコマンドでした",false));
                break;
        }
    }
}
