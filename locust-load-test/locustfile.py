from locust.clients import LocustResponse
from locust import HttpUser, TaskSet, task, between, User
import random
import string
import time


def generate_data():
    return {
        "username": "user_" + ''.join(random.choices(string.ascii_lowercase + string.digits, k=8)),
        "timestamp": str(int(time.time()))
    }


class ServiceBehavior(TaskSet):

    def on_start(self):
        self.order_uuid = None

    @task
    def order_operations(self):
        response: LocustResponse = self.client.post(f"{self.service_path}/order", name=f"[{self.service_path}]_create", json=generate_data())

        if response.status_code == 201:
            self.order_uuid = response.content.decode("utf-8")

            for _ in range(random.randint(2, 3)):
                response = self.client.get(f"{self.service_path}/order/{self.order_uuid}", name=f"[{self.service_path}]_random_get")
                if response.status_code != 200:
                    print("Expected 200, but got", response.status_code)

            response = self.client.get(f"{self.service_path}/order/delete/{self.order_uuid}", name=f"[{self.service_path}]_then_delete")
            if response.status_code != 200:
                print("Expected 200, but got", response.status_code)

            for _ in range(random.randint(2, 3)):
                response = self.client.get(f"{self.service_path}/order/{self.order_uuid}", name=f"[{self.service_path}]_find after_delete")
                if response.status_code != 404:
                    print("Expected 404, but got", response.status_code)
        else:
            print("Error, status", response.status_code)


class WebsiteUser(HttpUser):
    wait_time = between(1, 3)
    host = "http://127.0.0.1"

    service_paths = ["/cf", "/cf-db", "/redis", "/cf-kafka", "/hzct"]

    def on_start(self):
        self.client.verify = False

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)

        for service_path in self.service_paths:
            task_set_class = type(
                f"{service_path.replace('/', '').capitalize()}Behavior",
                (ServiceBehavior,),
                {"service_path": service_path}
            )
            self.tasks.append(task_set_class)
