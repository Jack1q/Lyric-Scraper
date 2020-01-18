package webscrape;

import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *  This program scrapes musixmatch.com for lyrics
 *  and formats them line by line. It's still buggy
 *  because I track lines by capital letters, which
 *  do not always indicate a new line in music
 *
 * @author Jack Donofrio
 *
 */

public class LyricScraper
{
  public static void main(String[] args)
  {
    Scanner input = new Scanner(System.in);

    System.out.println("Song name?");
    final String rawQuerySong = input.nextLine();
    String querySong = spaceReplace(rawQuerySong);

    System.out.println("Artist name?");
    final String rawQueryArtist = input.nextLine();
    String queryArtist = spaceReplace(rawQueryArtist);

    System.out.println("Language?");
    final String queryLang = input.nextLine();

    String url = "";
    if (queryLang.toLowerCase().equals("english"))
      url =
        "https://www.musixmatch.com/lyrics/" + queryArtist + "/" + querySong;
    else if (queryLang.toLowerCase().equals("spanish"))
      url =
        "https://www.musixmatch.com/lyrics/" + queryArtist + "/" + querySong
          + "/translation/spanish";

    // this is just so I can see that it gets the right url
    System.out.println(url);

    try
    {
      final Document document = Jsoup.connect(url).get();

      int count = 0;
      for (Element row : document.select(".mxm-lyrics"))
      {
        if (count < 1)
        {
          String raw = row.select(".mxm-lyrics__content").text();
          String result = "";
          System.out
            .println("\n" + rawQuerySong + " by " + rawQueryArtist + ": \n");
          for (int i = 0; i < raw.length(); i++)
          {
            if (!raw.substring(i, i + 1)
              .equals(raw.substring(i, i + 1).toLowerCase())
              && !raw.substring(i, i + 1).equals("I"))
              result += "\n";
            if (raw.substring(i, i + 1).equals("\n"))
              result += "\n";
            result += raw.substring(i, i + 1);

          }
          System.out.println(result);
        }
        count++;
      }
    }

    catch (Exception ex)
    {
      System.out.print("Song \"" + rawQuerySong + "\" by " + rawQueryArtist
        + " cannot be located");

    }

  }

  // replaces spaces with dashes for links
  public static String spaceReplace(String str)
  {
    String query = "";
    for (int i = 0; i < str.length(); i++)
    {
      if (str.substring(i, i + 1).equals(" "))
        query += "-";
      else
        query += str.substring(i, i + 1);
    }
    return query;
  }

}
