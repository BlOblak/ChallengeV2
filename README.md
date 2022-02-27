# ChallengeV2

The application is written in Springboot framework as a REST API.
It is used for storing measurements the energy assets are producing. The application supports the basic REST API operations for a MongoDb time-series collection(without update and delete).
Along REST API it enables that energy assets send their measurements through RabbitMq messaging system asynchronously.
It is also using Redis caching system for storing the latest received measurement.

To run the application you must set the environmental variables first. It is described in details in the application documentation included in the project -> [Link](https://github.com/BlOblak/ChallengeV2/blob/main/docs/ChallengeV2-documentation.pdf)

