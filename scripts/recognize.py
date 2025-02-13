# recognize.py
import sys
import json
import cv2
from insightface.app import FaceAnalysis

def main(image_path):
    # Initialize InsightFace (here we use the "buffalo_l" model, which is popular in InsightFace)
    app = FaceAnalysis(name='buffalo_l')
    app.prepare(ctx_id=0, nms=0.4)

    # Load the image
    img = cv2.imread(image_path)
    if img is None:
        print(json.dumps({"error": "Could not read image"}))
        sys.exit(1)

    # Run face analysis.
    faces = app.get(img)
    results = []
    for face in faces:
        result = {
            "bbox": face.bbox.tolist(),  # bounding box coordinates
            "gender": face.gender,         # usually 0 (female) or 1 (male)
            "age": face.age,
            "embedding": face.embedding.tolist()  # face embedding (vector)
        }
        results.append(result)

    print(json.dumps(results))

if __name__ == '__main__':
    if len(sys.argv) != 2:
        print("Usage: python recognize.py <image_path>")
        sys.exit(1)
    main(sys.argv[1])
