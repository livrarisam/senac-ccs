public aspect ThinkFastGameLogger {
    pointcut participantPlay() : execution (public void *.ThinkFastGame.play( String, String, javax.servlet.AsyncContext ));

        after() : participantPlay() {

            System.out.println("Participant just joined the game!");

        }
}