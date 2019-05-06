package su.svn.href.models.dto;

public class AnswerBadRequest extends Answer
{
    public AnswerBadRequest(String response)
    {
        super("Bad Request: " + response);
    }
}
