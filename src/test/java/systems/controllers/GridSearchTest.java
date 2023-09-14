package systems.controllers;

import entities.mobs.Ghost;
import entities.mobs.Mob;
import entities.mobs.Zombie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import systems.spawning.PlacementRules;
import utils.Pair;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GridSearchTest {
    private static boolean isPath(GridSearch search, List<Pair<Integer, Integer>> fields) {
        for(int i = 1; i < fields.size(); i++)
            if(!fields.get(i - 1).equals(search.getPredecessor(fields.get(i))))
                return false;
        return true;
    }

    private static void assertPath(GridSearch search, List<Pair<Integer, Integer>> fields) {
        assertTrue(isPath(search, fields));
    }

    private static boolean anyIsPath(GridSearch search, List<List<Pair<Integer, Integer>>> candidatePaths) {
        return candidatePaths.stream().anyMatch(candidate -> isPath(search, candidate));
    }

    private static void assertAnyPath(GridSearch search, List<List<Pair<Integer, Integer>>> candidatePaths) {
        assertTrue(anyIsPath(search, candidatePaths));
    }

    @Test
    void testNoPredecessorsAreSetIfSearchWasNotRun() {
        GridSearch search = new GridSearch(mock(PlacementRules.class), Set.of());
        assertNull(search.getPredecessor(new Pair<>(42, 84))); // just some arbitrary pairs
        assertNull(search.getPredecessor(new Pair<>(80, 15)));
        assertNull(search.getPredecessor(new Pair<>(1177, 5895)));
    }

    @Test
    void testCannotRunSearchWithNegativeDistanceLimit() {
        GridSearch search = new GridSearch(mock(PlacementRules.class), Set.of());
        assertThrows(IllegalArgumentException.class, () -> search.runFrom(new Pair<>(7, 7), -5));
    }

    @Nested
    class SimpleShortestPathTest {
        private GridSearch search;

        @BeforeEach
        void setUp() {
            PlacementRules rules = mock(PlacementRules.class);
            when(rules.isForbiddenAt(notNull(), notNull())).thenReturn(false); // all fields are available
            search = new GridSearch(rules, Set.of());
        }

        @Test
        void testSourceFieldIsItsOwnPredecessorRegardlessOfDistance() {
            Pair<Integer, Integer> source = new Pair<>(-3, 4);
            search.runFrom(source, 1);
            assertEquals(source, search.getPredecessor(source));
        }

        @Test
        void testSourceFieldIsThePredecessorOfNeighboringFieldsWhenDistanceLimitIsSufficient() {
            Pair<Integer, Integer> source = new Pair<>(-3, 4);
            search.runFrom(source, 2);
            assertEquals(source, search.getPredecessor(new Pair<>(-4, 3)));
            assertEquals(source, search.getPredecessor(new Pair<>(-4, 4)));
            assertEquals(source, search.getPredecessor(new Pair<>(-4, 5)));
            assertEquals(source, search.getPredecessor(new Pair<>(-3, 3)));
            assertEquals(source, search.getPredecessor(new Pair<>(-3, 5)));
            assertEquals(source, search.getPredecessor(new Pair<>(-2, 3)));
            assertEquals(source, search.getPredecessor(new Pair<>(-2, 4)));
            assertEquals(source, search.getPredecessor(new Pair<>(-2, 5)));
        }

        @Test
        void testSearchFindsASimplePathWithATightDistanceLimit() {
            Pair<Integer, Integer> source = new Pair<>(-1, 1);
            Pair<Integer, Integer> intermediate = new Pair<>(0, 1);
            Pair<Integer, Integer> destination = new Pair<>(1, 1);
            search.runFrom(source, 2); // the distance is exactly 2 and so is the limit
            assertPath(search, List.of(source, intermediate, destination));
        }

        @Test
        void testSearchFindsASimplePathWithALooseDistanceLimit() {
            Pair<Integer, Integer> source = new Pair<>(-1, 1);
            Pair<Integer, Integer> intermediate = new Pair<>(0, 1);
            Pair<Integer, Integer> destination = new Pair<>(1, 1);
            search.runFrom(source, 10); // the distance is exactly 2 and the limit is looser
            assertPath(search, List.of(source, intermediate, destination));
        }

        @Test
        void testSearchDoesNotFindASimplePathWithAnInsufficientDistanceLimit() {
            Pair<Integer, Integer> source = new Pair<>(-1, 1);
            Pair<Integer, Integer> destination = new Pair<>(1, 1);
            search.runFrom(source, 1); // the distance is exactly 2 and the limit is tighter
            assertNull(search.getPredecessor(destination));
        }

        @Test
        void testSearchFindsAStraightHorizontalPath() {
            Pair<Integer, Integer> source = new Pair<>(-1, 2);
            Pair<Integer, Integer> firstIntermediate = new Pair<>(0, 2);
            Pair<Integer, Integer> secondIntermediate = new Pair<>(1, 2);
            Pair<Integer, Integer> destination = new Pair<>(2, 2);
            search.runFrom(source, 3);
            assertPath(search, List.of(firstIntermediate, secondIntermediate, destination));
        }

        @Test
        void testSearchFindsAStraightVerticalPath() {
            Pair<Integer, Integer> source = new Pair<>(-1, 2);
            Pair<Integer, Integer> firstIntermediate = new Pair<>(-1, 1);
            Pair<Integer, Integer> secondIntermediate = new Pair<>(-1, 0);
            Pair<Integer, Integer> destination = new Pair<>(-1, -1);
            search.runFrom(source, 3);
            assertPath(search, List.of(source, firstIntermediate, secondIntermediate, destination));
        }

        @Test
        void testSearchFindsAStraightDiagonalPath() {
            Pair<Integer, Integer> source = new Pair<>(-1, 2);
            Pair<Integer, Integer> firstIntermediate = new Pair<>(0, 3);
            Pair<Integer, Integer> secondIntermediate = new Pair<>(1, 4);
            Pair<Integer, Integer> destination = new Pair<>(2, 5);
            search.runFrom(source, 5); // 5 > 3sqrt(2)
            assertPath(search, List.of(source, firstIntermediate, secondIntermediate, destination));
        }

        @Test
        void testSearchFindsAPathWithOneDiagonalBreak() {
            Pair<Integer, Integer> source = new Pair<>(-1, 2);
            Pair<Integer, Integer> upperIntermediate = new Pair<>(0, 3);
            Pair<Integer, Integer> lowerIntermediate = new Pair<>(0, 2);
            Pair<Integer, Integer> destination = new Pair<>(1, 3);
            var upperPath = List.of(source, upperIntermediate, destination); // there are two shortest paths
            var lowerPath = List.of(source, lowerIntermediate, destination); // we want the search to find one of them
            search.runFrom(source, 10);
            assertAnyPath(search, List.of(upperPath, lowerPath));
        }

        @Test
        void testRerunningTheSearchWithALooserLimitSetsPredecessorsForFartherDestinations() {
            Pair<Integer, Integer> source = new Pair<>(-1, -1);
            Pair<Integer, Integer> destination = new Pair<>(-10, -10);
            search.runFrom(source, 3); // the distance limit is too tight to reach the destination
            assertNull(search.getPredecessor(destination));
            search.runFrom(source, 20); // let us rerun the search with a looser limit
            assertNotNull(search.getPredecessor(destination));
        }

        @Test
        void testRerunningTheSearchWithATighterLimitResetsPredecessorsForCloserDestinations() {
            Pair<Integer, Integer> source = new Pair<>(-1, -1);
            Pair<Integer, Integer> destination = new Pair<>(-10, -10);
            search.runFrom(source, 20); // the distance limit is loose enough to reach the destination
            assertNotNull(search.getPredecessor(destination));
            search.runFrom(source, 3); // let us rerun the search with a tighter limit
            assertNull(search.getPredecessor(destination));
        }
    }

    @Nested
    class WithObstacleTest {
        private Pair<Integer, Integer> source, destination, obstacle;
        private GridSearch search;

        @BeforeEach
        void setUp() {
            source = new Pair<>(0, 0);
            destination = new Pair<>(-2, -2);
            obstacle = new Pair<>(-1, -1);

            search = new GridSearch(rulesWithObstacle(obstacle), Set.of());
        }

        private static PlacementRules rulesWithObstacle(Pair<Integer, Integer> obstacleField) {
            PlacementRules rules = mock(PlacementRules.class);
            when(rules.isOccupied(notNull())).thenReturn(false);
            when(rules.isOccupied(obstacleField)).thenReturn(true);
            return rules;
        }

        @Test
        void testObstacleIsNotVisited() {
            search.runFrom(new Pair<>(0, 0), 10); // the limit is more than enough to reach the obstacle
            assertNull(search.getPredecessor(obstacle));
        }

        @Test
        void testSearchFindsAWayAroundTheObstacleWithSufficientDistanceLimit() {
            var upperPath = List.of(source, new Pair<>(-1, 0), new Pair<>(-2, -1), destination);
            var lowerPath = List.of(source, new Pair<>(0, -1), new Pair<>(-1, -2), destination);
            search.runFrom(source, 10); // the limit is more than enough to reach the destination
            assertAnyPath(search, List.of(upperPath, lowerPath));
        }

        @Test
        void testSearchDoesNotFindAWayAroundTheObstacleWithInsufficientDistanceLimit() {
            search.runFrom(source, 3); // the distance is 2 + sqrt(2), so 3 will not be enough of a limit
            assertNull(search.getPredecessor(destination));
        }
    }

    @Nested
    class SpecificMobsTest {
        private Pair<Integer, Integer> source, destination, special;
        private List<Pair<Integer, Integer>> upperPath, lowerPath;
        private PlacementRules rules;

        @BeforeEach
        void setUp() {
            source = new Pair<>(0, 0);
            destination = new Pair<>(-2, -2);
            special = new Pair<>(-1, -1);
            upperPath = List.of(source, new Pair<>(-1, 0), new Pair<>(-2, -1), destination);
            lowerPath = List.of(source, new Pair<>(0, -1), new Pair<>(-1, -2), destination);

            rules = rulesWithIllegalFieldsForSpecificMobs(
                Set.of(special),
                Set.of(Zombie.class) // Zombies are forbidden on the special field
            );
        }

        @SuppressWarnings("SuspiciousMethodCalls")
        private static PlacementRules rulesWithIllegalFieldsForSpecificMobs(
            Set<Pair<Integer, Integer>> illegalFields,
            Set<Class<? extends Mob>> mobs
        ) {
            PlacementRules rules = mock(PlacementRules.class);
            when(rules.isForbiddenAt(notNull(), notNull())).thenAnswer(
                invocation -> illegalFields.contains(invocation.getArguments()[1])
                    && mobs.contains(invocation.getArguments()[0])
            );
            return rules;
        }

        @Test
        void testSpecialFieldIsVisitedWhenSearchDoesNotGuideForbiddenMobs() {
            // This grid search is for Ghosts - they are allowed on the special field
            GridSearch search = new GridSearch(rules, Set.of(Ghost.class));
            search.runFrom(new Pair<>(0, 0), 10); // the limit is more than enough to reach the obstacle
            assertNotNull(search.getPredecessor(special));
        }

        @Test
        void testSpecialFieldIsNotVisitedWhenSearchGuidesForbiddenMobs() {
            // This grid search is for Zombies - they are forbidden on the special field
            GridSearch search = new GridSearch(rules, Set.of(Zombie.class));
            search.runFrom(new Pair<>(0, 0), 10); // the limit is more than enough to reach the special field
            assertNull(search.getPredecessor(special));
        }

        @Test
        void testSearchThatGuidesForbiddenMobsFindsAWayAroundTheSpecialFieldWithSufficientDistanceLimit() {
            // This grid search is for Zombies - they are forbidden on the special field
            GridSearch search = new GridSearch(rules, Set.of(Zombie.class));
            search.runFrom(source, 10); // the limit is more than enough to reach the destination
            assertAnyPath(search, List.of(upperPath, lowerPath));
        }

        @Test
        void testSearchThatGuidesForbiddenMobsDoesNotFindAWayAroundTheSpecialFieldWithInsufficientDistanceLimit() {
            // This grid search is for Zombies - they are forbidden on the special field
            GridSearch search = new GridSearch(rules, Set.of(Zombie.class));
            search.runFrom(source, 3); // the distance is 2 + sqrt(2), so 3 will not be enough of a limit
            assertNull(search.getPredecessor(destination));
        }
    }
}