package Crowd_Pressure_AGH;

/** Enum class containing simulation possibilities*/
public enum SimulationTypes {

    SYM_ONE_WALL_TWO_GROUPS {
        @Override
        public String toString() {
            return "Two Groups of people against the Wall";
        }
    },

    SYM_ROOM_WITHOUT_OBSTACLE {
        @Override
        public String toString() {
            return "Room Without Obstacle";
        }
    },

    SYM_ROOM_WITH_HORIZONTAL_OBSTACLE {
        @Override
        public String toString() {
            return "Room With Horizontal Obstacle";
        }
    },

    SYM_ROOM_WITH_VERTICAL_OBSTACLE {
        @Override
        public String toString() {
            return "Room With Vertical Obstacle";
        }
    },

    SYM_ROOM_WITH_CORRIDOR {
        @Override
        public String toString() {
            return "Room With Corridor";
        }
    },

    SYM_ROOM_WITH_CORRIDOR_AND_COLUMN {
        @Override
        public String toString() {
            return "Room With Corridor and Column";
        }
    },

    SYM_THREE_GROUPS {
        @Override
        public String toString() {
            return "Three Groups In Front Of Each Other ";
        }
    },

    SYM_EMPTY {
        @Override
        public String toString() {
            return "Empty";
        }
    };

}
