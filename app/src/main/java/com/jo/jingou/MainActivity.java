package com.jo.jingou;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class MainActivity extends Activity {

    private static final String APP_ID="wxbd3e6bba8efbae73";

    private static int mTargetScene = SendMessageToWX.Req.WXSceneSession;
    private static int mTargetSceneCricle = SendMessageToWX.Req.WXSceneTimeline;

    private Button button1,button2,button3,button4;
    private IWXAPI iwxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regToWx();

        button1 = (Button) this.findViewById(R.id.tosharefriend);
        button2 = (Button) this.findViewById(R.id.tosharecricle);
        button3 = (Button) this.findViewById(R.id.toshare_music);
        button4 = (Button) this.findViewById(R.id.toshare_video);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWeb();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTextToCricle();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toshareMusic();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toshareVideo();
            }
        });
    }

    private void toshareVideo() {
        WXVideoObject musicObject = new WXVideoObject();
        musicObject.videoUrl = "http://music.163.com/m/mv?id=5383969&userid=258307201&from=groupmessage";


        //用WXWebpageObject对象初始化一个WXMediaMessage 对象 填写标题 描述
        WXMediaMessage msg = new WXMediaMessage();

        msg.mediaObject = musicObject;
        msg.title ="三千里";
        msg.description = "C-BLOCK/ 西奥Sio / BBC";

        Bitmap thumb = BitmapFactory.decodeResource(this.getResources() , R.mipmap.ic_launcher);
        msg.thumbData = Util.bmpToByteArray(thumb , true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransation("video");
        req.message = msg;
        req.scene = mTargetScene;
        iwxapi.sendReq(req);

    }

    private void toshareMusic() {

        WXMusicObject musicObject = new WXMusicObject();
        musicObject.musicUrl = "http://music.163.com/#/song?id=494300869&userid=429917799";


        //用WXWebpageObject对象初始化一个WXMediaMessage 对象 填写标题 描述
        WXMediaMessage msg = new WXMediaMessage();

        msg.mediaObject = musicObject;
        msg.title ="消愁";
        msg.description = "毛不易";

        Bitmap thumb = BitmapFactory.decodeResource(this.getResources() , R.mipmap.ic_launcher);
        msg.thumbData = Util.bmpToByteArray(thumb , true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransation("music");
        req.message = msg;
        req.scene = mTargetScene;
        iwxapi.sendReq(req);

    }

    private void shareWeb() {

        //初始化一个WXWebpageObject对象填写 url
        WXWebpageObject webpage  = new WXWebpageObject();
        webpage.webpageUrl = "https://www.baidu.com/";

        //用WXWebpageObject对象初始化一个WXMediaMessage 对象 填写标题 描述
        WXMediaMessage msg = new WXMediaMessage(webpage);

        msg.title ="百度";
        msg.description = "搜搜搜";
        Bitmap thumb = BitmapFactory.decodeResource(this.getResources() , R.mipmap.ic_launcher);
        msg.thumbData = Util.bmpToByteArray(thumb , true);

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransation("webpage");
        //段用于唯一标识符
        req.message = msg;
        req.scene = mTargetScene;

        iwxapi.sendReq(req);
    }

    private void shareTextToCricle() {
        WXTextObject textObject = new WXTextObject();
        textObject.text = "hello 美女环环!";

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.description = "hello world!";

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransation("text");
        req.message = msg;
        req.scene = mTargetSceneCricle;

        iwxapi.sendReq(req);


    }

    private void shareText() {

        WXTextObject textObject = new WXTextObject();
        textObject.text = "hello world!";

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.description = "hello world!";

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransation("text");
        req.message = msg;
        req.scene = mTargetScene;

        iwxapi.sendReq(req);

    }

    private void regToWx() {
        iwxapi = WXAPIFactory.createWXAPI(this,APP_ID,true);

        iwxapi.registerApp(APP_ID);
    }

    private  String buildTransation(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
