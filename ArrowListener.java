/**
 * ArrowListener establishes the name of the methods that
 * respond to the four arrow keys.
 * 
 * @author  Dave Feinberg
 * @author  Richard Page
 * @author  Susan King     Added documentation
 * @author Richa Bhattacharya
 * @version 02/08/17
 */
public interface ArrowListener
{
    /**
     * Responses to the up arrow.
     */
    void upPressed();

    /**
     * Responses to the down arrow.
     */
    void downPressed();

    /**
     * Responses to the left arrow.
     */
    void leftPressed();

    /**
     * Responses to the right arrow.
     */
    void rightPressed();
}