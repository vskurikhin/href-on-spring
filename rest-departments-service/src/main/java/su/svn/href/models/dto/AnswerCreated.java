package su.svn.href.models.dto;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;

public class AnswerCreated extends Answer
{
    public AnswerCreated(HttpServletResponse response, String location, Long id)
    {
        super("Created");
        response.addHeader("Location", location + '/' + id);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    public AnswerCreated(HttpServletResponse response, String location, String id)
    {
        super("Created");
        response.addHeader("Location", location + '/' + id);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }
}
