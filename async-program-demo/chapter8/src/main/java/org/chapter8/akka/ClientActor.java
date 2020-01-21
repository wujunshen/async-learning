package org.chapter8.akka;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ClientActor extends UntypedActor {
  private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

  private ActorSelection actorRef =
      getContext()
          .actorSelection("akka.tcp://AkkaRemoteServer@127.0.0.1:2552/user/CalculatorActor");

  @Override
  public void onReceive(Object message) {
    if (message.equals("DoCalcs")) {
      log.info("Got a calc job, local calculator");

      log.info("{}", actorRef);
      actorRef.tell(new Messages.Sum(1, 2), getSelf());
    } else if (message instanceof Messages.Result) {
      Messages.Result result = (Messages.Result) message;
      log.info("Got result back from calculator: {}", result.getResult());
    }
  }
}
