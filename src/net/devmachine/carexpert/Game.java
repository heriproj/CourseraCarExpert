package net.devmachine.carexpert;

/**
 * Main object. Holds questions and relevant answers.
 */
class Game
{
    private static int SIZE           = 3;

    private Question   questions[]    = new Question[SIZE];
    private Answer     answers[]      = new Answer[SIZE];

    private int        questionIndex  = 0;
    private int        answerIndex    = 0;
    private int        processedIndex = 0;

    public Game()
    {
    }

    public void addQuestion(Question question)
    {
        questions[questionIndex++] = question;
    }

    public void addAnswer(Answer answer)
    {
        answers[answerIndex++] = answer;
    }

    public Question nextQuestion()
    {
        return questions[processedIndex++];
    }

    public boolean isFull()
    {
        return questionIndex == SIZE;
    }

    public boolean isOver()
    {
        return answerIndex == SIZE;
    }

    public int getScore()
    {
        if (!isOver()) {
            throw new RuntimeException("Game is not over.");
        }

        int score = 0;
        for (Answer answer : answers) {
            score += getAnswerScore(answer);
        }

        return score;
    }

    private int getAnswerScore(Answer answer)
    {
        if (answer.isCorrect()) {
            return 4 - answer.getAttempt();
        }

        return 0;
    }
}
