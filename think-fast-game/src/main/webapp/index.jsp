<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Think Fast Game</title>
        <script type="text/javascript" src="resources/js/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src="resources/js/knockout-2.0.0.js"></script>
    </head>
    <body>
        <h1>Think Fast Game</h1>
        <div id="participant">
            <h2>Insert your name and click start to begin:</h2>
            <input type="text" name="participant" />
            <input type="button" value="start" data-bind="click: play" />
        </div>
        <br/>
        <div id="survey">
            <span data-bind="text: question">Qual a capital da Rússia?</span>
            <ul data-bind="foreach: answers">
                <li style="list-style: none;">
                    <input type="radio" name="answer" data-bind="click: answer" />
                    <span data-bind="text: $data">Moscou</span>
                </li>
            </ul>
            <span id="message"></span>
            <script>
                var ThinkFast = function () {
                    var self = this;
                    self.participant = ko.observable();
                    self.question = ko.observable();
                    self.answers = ko.observableArray([]);

                    self.play = function() {
                        $.getJSON("/thinkfast", {action: "play", name: self.participant()}, function(data) {
                            self.question(data.description);
                            self.answers.removeAll();
                            $.map(data.answers, function(answer) {
                                self.answers.push(answer);
                            });
                        });
                    }

                    self.bind = function() {

                    }
                    self.answer = function(answer) {
                        
                    }
                }
                ko.applyBindings(new ThinkFast());
            </script>
        </div>
    </body>
</html>
