using UnityEngine;
using System.Collections;

public class Character : MonoBehaviour {

	public int initiative = 5;
	public int hpTotal = 10;
	public int hpCurrent;

	public Skill[] skills = {new Fireball(), new Hit()};
	// Use this for initialization
	void Start () {
		hpCurrent = hpTotal;
	}
	
	// Update is called once per frame
	void Update () {
	
	}
}
