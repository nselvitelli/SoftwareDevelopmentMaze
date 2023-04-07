# Labyrinth (Nick + Duncan)

## To make
- Referee section

## Table of Contents
| Name | Description |
| ---- | ----------- |
| [Maze](Maze/src/README.md) |  |
| [10](10) |  |
| [9](9) |  |
| [8](8) |  |
| [7](7) |  |

## Client-Server Architecture
```
Client                                                            Server

┌────────────────────────────────────────────┐                    ┌──────────────────────────────────────────┐
│                                            │                    │                                          │
│                                            │                    │                                          │
│  Player ─────────────────► ProxyReferee    │ ◄────────────────► │   ProxyPlayer ─────┐                     │
│                                            │                    │                    │                     │
│                                            │                    │                    │                     │
│                                            │                    │                    │                     │
│  Player ─────────────────► ProxyReferee    │ ◄────────────────► │   ProxyPlayer ─────┼──────► Referee      │
│                                            │                    │                    │                     │
│                                            │                    │                    │                     │
│                                            │                    │                    │                     │
│  Player ─────────────────► ProxyReferee    │ ◄────────────────► │   ProxyPlayer ─────┘                     │
│                                            │                    │                                          │
│                                            │                    │                                          │
│                                            │                    │                                          │
│  ...                       ...             │                    │   ...                                    │
│                                            │                    │                                          │
└────────────────────────────────────────────┘                    └──────────────────────────────────────────┘```