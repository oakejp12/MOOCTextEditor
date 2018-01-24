/**
 * 
 */
package spelling;

import java.util.List;

/**
 * @author Christine
 *
 */
public interface AutoComplete {
	List<String> predictCompletions(String prefix, int numCompletions);
}
