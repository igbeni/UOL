package br.com.igbeni.uol.exception;

import android.content.Context;

import br.com.igbeni.uol.R;
import br.com.igbeni.uol.data.exception.FeedItemNotFoundException;
import br.com.igbeni.uol.data.exception.NetworkConnectionException;

/**
 * Factory used to create error messages from an Exception as a condition.
 */
public class ErrorMessageFactory {

    private ErrorMessageFactory() {
        //empty
    }

    /**
     * Creates a String representing an error message.
     *
     * @param context   Context needed to retrieve string resources.
     * @param exception An exception used as a condition to retrieve the correct error message.
     * @return {@link String} an error message.
     */
    public static String create(Context context, Exception exception) {
        String message = context.getString(R.string.exception_message_generic);

        if (exception instanceof NetworkConnectionException) {
            message = context.getString(R.string.exception_message_no_connection);
        } else if (exception instanceof FeedItemNotFoundException) {
            message = context.getString(R.string.exception_message_feed_item_not_found);
        }

        return message;
    }
}
