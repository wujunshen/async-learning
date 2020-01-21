package example.akka.remote.client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client {
  public static void main(String[] args) {
    // 1. 创建Actor系统，会加载application.conf文件
    ActorSystem system = ActorSystem.create("AkkaRemoteClient", ConfigFactory.load());

    // 2. 创建Actor
    ActorRef client = system.actorOf(Props.create(ClientActor.class));

    // 3. 发送消息
    client.tell("DoCalcs", ActorRef.noSender());
    log.info("over");
  }
}
