package com.ansible.republic;

public class ActionCard {

    public enum ACTION {
        YAY(1), NAY(2), MEH(3), ROTATE(4);

        private int value;

        private ACTION(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    ACTION type;

    public ActionCard() {

    }

    public ActionCard(ACTION _type) {
        type = _type;
    }
}
