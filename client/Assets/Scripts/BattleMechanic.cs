using UnityEngine;
using System.Collections;

public class BattleMechanic : MonoBehaviour {

	private enum Phase
	{ START, PRE_TURN, PLAYER_TURN, ENEMY_TURN, POST_TURN, END}

	private Phase currentPhase = Phase.START;

	// Use this for initialization
	void Start () {

	}

	// Update is called once per frame
	void Update () {
		switch (currentPhase) {
		case Phase.START:
			break;
		case Phase.PRE_TURN:
			break;
		case Phase.PLAYER_TURN:
			break;
		case Phase.ENEMY_TURN:
			break;
		case Phase.POST_TURN:
			break;
		case Phase.END:
			break;
		}
	}
}
