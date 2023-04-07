# Server

This directory holds all files concerned with Server interactions between the Referee and remote Players.

### Files/Directories in this directory
| Name | Description |
| ---- | ----------- |
| [ProxyPlayer](ProxyPlayer.java) |  A proxy which abstracts the network layer for Players. Each ProxyPlayer runs on a referee server and is connected to a single ProxyReferee on a player client, forwarding API calls from the actual referee to the proxy referee and passing the responses back to the referee.  |
| [RefereeServer](RefereeServer.java) | Static server class which signs up players and creates a Referee that runs a game. |


### File Overview
![Image](../../../../readme-resources/Server.png)