object TimerAnonymous {
	def oncePerSecond(callback: () => Unit) {
		while (true) {
			callback(); Thread sleep 1000
		}
	}
	def timeFlies() {
	
	}
	def main(args: Array[String]) {
		oncePerSecond(() => 
			println("time flies like an arrow..."))
	}
}
