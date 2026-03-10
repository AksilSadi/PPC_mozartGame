error id: file:///C:/Users/DELL/OneDrive/Bureau/sorbonne/M2/S2/PPC/mozartGame_code/src/main/scala/Conductor.scala:
file:///C:/Users/DELL/OneDrive/Bureau/sorbonne/M2/S2/PPC/mozartGame_code/src/main/scala/Conductor.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 572
uri: file:///C:/Users/DELL/OneDrive/Bureau/sorbonne/M2/S2/PPC/mozartGame_code/src/main/scala/Conductor.scala
text:
```scala
package upmc.akka.ppc

import akka.actor._

object Conductor {

    def jeterDeuxDes(): (Int, Int) = {
        val de1 = scala.util.Random.nextInt(6) + 1
        val de2 = scala.util.Random.nextInt(6) + 1
        (de1, de2)
    }
  
}

class ConductorActor () extends Actor {

    import ConductorActor._
    import ProviderActor._

    public compteur: Int = 1

    def receive = {
        case "StartGame" => {
            println("Conductor a recu message de StartGame")
            val (de1, de2) = jeterDeuxDes()
            ProviderActor ! GetMeasure(de1+de2, compte@@ur)
        }
    }

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 