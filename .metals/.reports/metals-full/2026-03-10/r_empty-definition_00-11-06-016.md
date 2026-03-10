error id: file:///C:/Users/DELL/OneDrive/Bureau/sorbonne/M2/S2/PPC/mozartGame_code/src/main/scala/Player.scala:context.
file:///C:/Users/DELL/OneDrive/Bureau/sorbonne/M2/S2/PPC/mozartGame_code/src/main/scala/Player.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -math/context.
	 -javax/sound/midi/context.
	 -javax/sound/midi/ShortMessage.context.
	 -scala/concurrent/duration/context.
	 -DataBaseActor.context.
	 -PlayerActor.context.
	 -context.
	 -scala/Predef.context.
offset: 1365
uri: file:///C:/Users/DELL/OneDrive/Bureau/sorbonne/M2/S2/PPC/mozartGame_code/src/main/scala/Player.scala
text:
```scala

package upmc.akka.ppc

import math._

import javax.sound.midi._
import javax.sound.midi.ShortMessage._

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global

import akka.actor.{Props, Actor, ActorRef, ActorSystem}


object PlayerActor {
  case class MidiNote (pitch:Int, vel:Int, dur:Int, at:Int) 
  val info = MidiSystem.getMidiDeviceInfo().filter(_.getName == "Gervill").headOption
  // or "SimpleSynth virtual input" or "Gervill"
  val device = info.map(MidiSystem.getMidiDevice).getOrElse {
    println("[ERROR] Could not find Gervill synthesizer.")
    sys.exit(1)
}

val rcvr = device.getReceiver()

/////////////////////////////////////////////////
def note_on (pitch:Int, vel:Int, chan:Int): Unit = {
    val msg = new ShortMessage
    msg.setMessage(NOTE_ON, chan, pitch, vel)
    rcvr.send(msg, -1)
}

def note_off (pitch:Int, chan:Int): Unit = {
    val msg = new ShortMessage
    msg.setMessage(NOTE_ON, chan, pitch, 0)
    rcvr.send(msg, -1)
}

}

//////////////////////////////////////////////////

class PlayerActor () extends Actor{
  import DataBaseActor._
  import PlayerActor._
  device.open()

  def receive = {
    case Measure (l) => {
      
    }
    case MidiNote(p,v, d, at) => {
      context.system.scheduler.scheduleOnce ((at) milliseconds) (note_on (p,v,10))
      conte@@xt.system.scheduler.scheduleOnce ((at+d) milliseconds) (note_off (p,10))
    }
  }
}


```


#### Short summary: 

empty definition using pc, found symbol in pc: 