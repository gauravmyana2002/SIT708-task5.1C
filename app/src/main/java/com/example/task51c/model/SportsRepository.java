package com.example.task51c.model;

import com.example.task51c.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class SportsRepository {
    private SportsRepository() {
    }

    public static List<NewsItem> getAllStories() {
        List<NewsItem> items = new ArrayList<>();
        items.add(new NewsItem(1, "City closes late derby with stoppage-time winner",
                "A fast-paced football derby swung twice before a composed finish sealed the headline match of the weekend. Coaches praised the bench impact and defensive shape in the closing stages.",
                "Football", R.drawable.story_football, true, "Featured Match"));
        items.add(new NewsItem(2, "Tigers build momentum after fourth straight away win",
                "The basketball side continued its road streak with efficient perimeter shooting and strong transition defense. Analysts noted better ball movement and cleaner shot selection.",
                "Basketball", R.drawable.story_basketball, true, "Top Story"));
        items.add(new NewsItem(3, "Australia chase down testing target in rain-hit thriller",
                "A disciplined cricket chase held together under pressure after a revised total kept the finish tight. The captain highlighted calm batting partnerships as the key turning point.",
                "Cricket", R.drawable.story_cricket, true, "Featured Match"));
        items.add(new NewsItem(4, "Young striker earns national call-up after breakout month",
                "Selection came after a run of high-energy performances, with staff citing pressing discipline, movement off the ball, and confidence in front of goal.",
                "Football", R.drawable.story_football_alt, false, "Latest News"));
        items.add(new NewsItem(5, "Veteran guard returns ahead of conference finals push",
                "Medical staff cleared the player for limited minutes, giving the rotation a welcome boost and adding experience to late-game decision-making.",
                "Basketball", R.drawable.story_basketball_alt, false, "Latest News"));
        items.add(new NewsItem(6, "Spin pair share seven wickets on difficult day-two pitch",
                "Cricket selectors were impressed by the control shown across long spells, with bounce and drift creating constant pressure through the middle session.",
                "Cricket", R.drawable.story_cricket_alt, false, "Latest News"));
        items.add(new NewsItem(7, "Grand Slam contender cruises into quarter-final showdown",
                "A sharp serve percentage and confident baseline play helped the favorite move through in straight sets, setting up a high-profile meeting next round.",
                "Tennis", R.drawable.story_tennis, false, "Latest News"));
        items.add(new NewsItem(8, "Constructor reveals aggressive upgrade package for next race",
                "Engineers believe the revised aero balance can improve qualifying pace without sacrificing long-run stability, raising hopes before a pivotal weekend.",
                "Formula 1", R.drawable.story_formula1, false, "Latest News"));
        return items;
    }

    public static List<NewsItem> getFeaturedStories() {
        List<NewsItem> featured = new ArrayList<>();
        for (NewsItem item : getAllStories()) {
            if (item.isFeatured()) {
                featured.add(item);
            }
        }
        return featured;
    }

    public static List<NewsItem> filterStories(String query) {
        List<NewsItem> filtered = new ArrayList<>();
        String normalized = query == null ? "" : query.trim().toLowerCase(Locale.US);
        for (NewsItem item : getAllStories()) {
            if (normalized.isEmpty()
                    || item.getCategory().toLowerCase(Locale.US).contains(normalized)
                    || item.getTitle().toLowerCase(Locale.US).contains(normalized)) {
                filtered.add(item);
            }
        }
        return filtered;
    }

    public static List<NewsItem> getRelatedStories(NewsItem current) {
        List<NewsItem> related = new ArrayList<>();
        for (NewsItem item : getAllStories()) {
            if (item.getId() != current.getId()
                    && (item.getCategory().equalsIgnoreCase(current.getCategory()) || related.size() < 3)) {
                related.add(item);
            }
        }
        return related;
    }

    public static NewsItem fromBookmark(int storyId, String title, String description, String category,
                                        int imageResId) {
        return new NewsItem(storyId, title, description, category, imageResId, false, "Saved Story");
    }
}
